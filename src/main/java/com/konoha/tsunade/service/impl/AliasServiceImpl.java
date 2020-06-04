package com.konoha.tsunade.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.konoha.tsunade.constants.ErrorCode;
import com.konoha.tsunade.constants.SystemParameter;
import com.konoha.tsunade.exceptions.BaseBusinessException;
import com.konoha.tsunade.exceptions.FileStorageException;
import com.konoha.tsunade.model.entity.AliasEntity;
import com.konoha.tsunade.model.request.AddAliasRequest;
import com.konoha.tsunade.model.response.AliasResponse;
import com.konoha.tsunade.repository.AliasRepository;
import com.konoha.tsunade.service.AliasService;
import com.konoha.tsunade.service.FileStorageService;
import com.konoha.tsunade.service.SystemParameterService;

@Service
public class AliasServiceImpl implements AliasService {

	@Autowired
	private AliasRepository aliasRepository;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private SystemParameterService systemParameterService;

	@Override
	public AliasResponse addAlias(final AddAliasRequest request, final MultipartFile file)
			throws BaseBusinessException {
		if (file == null || StringUtils.isBlank(request.getUsername())) {
			throw new BaseBusinessException(ErrorCode.INSUFFICIENT_PARAMETERS);
		}
		try {
			request.setProfilePicture(fileStorageService.storeFile(file, file.getOriginalFilename()));
			final AliasEntity aliasEntity = aliasRepository.save(AliasEntity.builder().username(request.getUsername())
					.profilePicture(request.getProfilePicture()).build());
			return convertAliasEntityToAliasResponse(aliasEntity);
		} catch (FileStorageException e) {
			throw new BaseBusinessException(ErrorCode.FILE_STORAGE_EXCEPTION);
		}
	}

	@Override
	public Page<AliasResponse> getAllAlias(final Integer pageNo) throws BaseBusinessException {
		Page<AliasEntity> aliases = aliasRepository.findAll(PageRequest.of(pageNo,
				systemParameterService.getSystemParameterInt(SystemParameter.PAGE_SIZE_ALIAS_LIST)));
		if (aliases == null || aliases.isEmpty()) {
			throw new BaseBusinessException(ErrorCode.NO_ALIASES_FOUND);
		}
		return aliases.map(this::convertAliasEntityToAliasResponse);
	}

	@Override
	public AliasResponse updateAlias(final String userName, final AddAliasRequest request, final MultipartFile file)
			throws BaseBusinessException {
		if (StringUtils.isBlank(userName)) {
			throw new BaseBusinessException(ErrorCode.NULL_USER);
		}
		AliasEntity aliasEntity = aliasRepository.findByUsernameAndActiveTrue(userName)
				.orElseThrow(() -> new BaseBusinessException(ErrorCode.USER_NOT_FOUND));

		if (StringUtils.isNotBlank(request.getUsername())) {
			aliasEntity.setUsername(request.getUsername());
		}

		if (file != null) {
			try {
				request.setProfilePicture(fileStorageService.storeFile(file, file.getOriginalFilename()));
			} catch (FileStorageException e) {
				throw new BaseBusinessException(ErrorCode.FILE_STORAGE_EXCEPTION);
			}
		}
		aliasEntity = aliasRepository.save(aliasEntity);
		return convertAliasEntityToAliasResponse(aliasEntity);
	}

	private AliasResponse convertAliasEntityToAliasResponse(final AliasEntity aliasEntity) {
		return AliasResponse.builder().username(aliasEntity.getUsername())
				.profilePicture(aliasEntity.getProfilePicture()).build();
	}
}
