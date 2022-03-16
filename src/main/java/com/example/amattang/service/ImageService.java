package com.example.amattang.service;

import com.example.amattang.domain.checkList.CheckList;
import com.example.amattang.domain.checkList.CheckListRepository;
import com.example.amattang.domain.image.Image;
import com.example.amattang.domain.image.ImageRepository;
import com.example.amattang.domain.user.User;
import com.example.amattang.exception.NotAccessUserException;
import com.example.amattang.template.AmazonS3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.amattang.exception.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final CheckListRepository checkListRepository;
    private final AmazonS3Template amazonS3Template;
    private final ImageRepository imageRepository;

    public void saveImage(User user, Long id, List<MultipartFile> files) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());
        List<Image> imageList = files.stream()
                .map(x -> new Image(checkList, amazonS3Template.saveToS3(x)))
                .collect(Collectors.toList());
        imageRepository.saveAll(imageList);
    }

    public void deleteImage(User user, Long id, List<Long> ids) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());

        ids.stream()
                .map(x -> imageRepository.findByCheckListId_IdAndId(id, x).orElseThrow(
                        () -> new IllegalArgumentException(NOT_EXIST_IMAGE_IN_CHECK_LIST + x))
                )
                .forEach(x -> amazonS3Template.deleteFromS3(x.getUrl().split("amazonaws.com/")[1]));

        imageRepository.deleteAllById(ids);

    }

    @Transactional
    public void changeMainImage(User user, Long id, Long imageId) {
        CheckList checkList = checkListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_CHECK_LIST));
        isCorrectAuthor(user.getId(), checkList.getUser().getId());
        Image image = imageRepository.findByCheckListId_IdAndId(id, imageId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_IMAGE_IN_CHECK_LIST + imageId));
        checkList.getImageList().stream()
                .filter(x -> x.isMain() == true)
                .forEach(x -> x.deleteMain());
        image.addMain();
    }

    public void isCorrectAuthor(String userId, String authorId) {
        if (!userId.equals(authorId)) {
            throw new NotAccessUserException(NOT_ACCESS_USER);
        }
    }
}
