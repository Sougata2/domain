package com.sougata.domain.mapper;

import com.sougata.domain.domain.activity.dto.ActivityDto;
import com.sougata.domain.domain.activity.entity.ActivityEntity;
import com.sougata.domain.domain.application.dto.ApplicationDto;
import com.sougata.domain.domain.application.entity.ApplicationEntity;
import com.sougata.domain.domain.devices.dto.DeviceDto;
import com.sougata.domain.domain.devices.entity.DeviceEntity;
import com.sougata.domain.domain.document.dto.DocumentDto;
import com.sougata.domain.domain.document.entity.DocumentEntity;
import com.sougata.domain.domain.formStages.dto.FormStageDto;
import com.sougata.domain.domain.formStages.entity.FormStageEntity;
import com.sougata.domain.domain.forms.dto.FormDto;
import com.sougata.domain.domain.forms.entity.FormEntity;
import com.sougata.domain.domain.lab.dto.LabDto;
import com.sougata.domain.domain.lab.entity.LabEntity;
import com.sougata.domain.domain.mandatoryDocument.dto.MandatoryDocumentsDto;
import com.sougata.domain.domain.mandatoryDocument.entity.MandatoryDocumentsEntity;
import com.sougata.domain.domain.quotation.dto.QuotationDto;
import com.sougata.domain.domain.quotation.entity.QuotationEntity;
import com.sougata.domain.domain.services.dto.ServiceDto;
import com.sougata.domain.domain.services.entity.ServiceEntity;
import com.sougata.domain.domain.specification.dto.SpecificationDto;
import com.sougata.domain.domain.specification.entity.SpecificationEntity;
import com.sougata.domain.domain.status.dto.StatusDto;
import com.sougata.domain.domain.status.entity.StatusEntity;
import com.sougata.domain.domain.workflow.dto.WorkFlowDto;
import com.sougata.domain.domain.workflow.entity.WorkFlowEntity;
import com.sougata.domain.file.dto.FileDto;
import com.sougata.domain.file.entity.FileEntity;
import com.sougata.domain.menu.dto.MenuDto;
import com.sougata.domain.menu.entity.MenuEntity;
import com.sougata.domain.role.dto.RoleDto;
import com.sougata.domain.role.entity.RoleEntity;
import com.sougata.domain.shared.MasterDto;
import com.sougata.domain.shared.MasterEntity;
import com.sougata.domain.subService.dto.SubServiceDto;
import com.sougata.domain.subService.entity.SubServiceEntity;
import com.sougata.domain.user.dto.UserDto;
import com.sougata.domain.user.entity.UserEntity;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Component
public class EntityDtoMapping {
    private final Map<Class<? extends MasterDto>, Class<? extends MasterEntity>> dtoToEntityMap;
    private final Map<Class<? extends MasterEntity>, Class<? extends MasterDto>> entityToDtoMap;

    public EntityDtoMapping() {
        entityToDtoMap = Map.ofEntries(
                Map.entry(MenuEntity.class, MenuDto.class),
                Map.entry(RoleEntity.class, RoleDto.class),
                Map.entry(UserEntity.class, UserDto.class),
                Map.entry(ServiceEntity.class, ServiceDto.class),
                Map.entry(SubServiceEntity.class, SubServiceDto.class),
                Map.entry(FormEntity.class, FormDto.class),
                Map.entry(ActivityEntity.class, ActivityDto.class),
                Map.entry(ApplicationEntity.class, ApplicationDto.class),
                Map.entry(DeviceEntity.class, DeviceDto.class),
                Map.entry(DocumentEntity.class, DocumentDto.class),
                Map.entry(LabEntity.class, LabDto.class),
                Map.entry(MandatoryDocumentsEntity.class, MandatoryDocumentsDto.class),
                Map.entry(QuotationEntity.class, QuotationDto.class),
                Map.entry(SpecificationEntity.class, SpecificationDto.class),
                Map.entry(StatusEntity.class, StatusDto.class),
                Map.entry(FileEntity.class, FileDto.class),
                Map.entry(FormStageEntity.class, FormStageDto.class),
                Map.entry(WorkFlowEntity.class, WorkFlowDto.class)
        );

        dtoToEntityMap = Map.ofEntries(
                Map.entry(MenuDto.class, MenuEntity.class),
                Map.entry(RoleDto.class, RoleEntity.class),
                Map.entry(UserDto.class, UserEntity.class),
                Map.entry(ServiceDto.class, ServiceEntity.class),
                Map.entry(SubServiceDto.class, SubServiceEntity.class),
                Map.entry(FormDto.class, FormEntity.class),
                Map.entry(ActivityDto.class, ActivityEntity.class),
                Map.entry(ApplicationDto.class, ApplicationEntity.class),
                Map.entry(DeviceDto.class, DeviceEntity.class),
                Map.entry(DocumentDto.class, DocumentEntity.class),
                Map.entry(LabDto.class, LabEntity.class),
                Map.entry(MandatoryDocumentsDto.class, MandatoryDocumentsEntity.class),
                Map.entry(QuotationDto.class, QuotationEntity.class),
                Map.entry(SpecificationDto.class, SpecificationEntity.class),
                Map.entry(StatusDto.class, StatusEntity.class),
                Map.entry(FileDto.class, FileEntity.class),
                Map.entry(FormStageDto.class, FormStageEntity.class),
                Map.entry(WorkFlowDto.class, WorkFlowEntity.class)
        );
    }
}
