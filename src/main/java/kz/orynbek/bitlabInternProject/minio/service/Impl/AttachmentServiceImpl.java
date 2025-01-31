package kz.orynbek.bitlabInternProject.minio.service.Impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kz.orynbek.bitlabInternProject.minio.DTO.AttachmentDTO;
import kz.orynbek.bitlabInternProject.minio.Entity.Attachment;
import kz.orynbek.bitlabInternProject.minio.mapper.AttachmentMapper;
import kz.orynbek.bitlabInternProject.minio.repository.AttachmentRepository;
import kz.orynbek.bitlabInternProject.minio.service.AttachmentService;
import kz.orynbek.bitlabInternProject.repositories.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final MinioClient minioClient;
    private final LessonRepository lessonRepository;
    private final AttachmentMapper attachmentMapper;
    @Value("${minio.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) {
        try {

            Attachment attachment = new Attachment();
            attachment.setId(attachment.getId());
            attachment.setName(file.getOriginalFilename());
            attachment.setUrl(attachment.getUrl());
            attachment.setLesson(attachment.getLesson());

            attachment = attachmentRepository.save(attachment);
            String fileName = DigestUtils.sha1Hex(attachment.getId() + "My_file");
            attachment.setName(fileName);

            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucket)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            attachmentRepository.save(attachment);
            return "Succesfully uploaded";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Some error on file upload";
    }

    @Override
    public List<AttachmentDTO> getFileList() {
        return attachmentMapper.toDTOList(attachmentRepository.findAll());
    }

    @Override
    public ByteArrayResource downloadFile(String fileName) {
        try{
            GetObjectArgs getObjectArgs = GetObjectArgs
                    .builder()
                    .bucket(bucket)
                    .object(fileName)
                    .build();

            InputStream stream = minioClient.getObject(getObjectArgs);
            byte[] byteArray = IOUtils.toByteArray(stream);
            stream.close();

            return new ByteArrayResource(byteArray);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;

    }
}
