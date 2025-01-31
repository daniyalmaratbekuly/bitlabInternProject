package kz.orynbek.bitlabInternProject.minio.mapper;

import kz.orynbek.bitlabInternProject.minio.DTO.AttachmentDTO;
import kz.orynbek.bitlabInternProject.minio.Entity.Attachment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    List<AttachmentDTO> toDTOList(List<Attachment> attachments);
    AttachmentDTO toDTO(Attachment attachment);
    Attachment toAttachment(AttachmentDTO attachmentDTO);
}
