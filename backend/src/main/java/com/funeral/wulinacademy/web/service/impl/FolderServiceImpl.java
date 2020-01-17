package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.entity.FolderTree;
import com.funeral.wulinacademy.web.model.FolderModify;
import com.funeral.wulinacademy.web.repository.FolderRepository;
import com.funeral.wulinacademy.web.repository.FolderTreeRepository;
import com.funeral.wulinacademy.web.service.FolderService;
import com.funeral.wulinacademy.web.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author FuneralObjects
 * CreateTime 2019-05-14 14:45
 */
@Service
@Transactional(readOnly = true)
public class FolderServiceImpl implements FolderService {


    @Resource
    private FolderRepository folderRepository;

    @Resource
    private FolderTreeRepository folderTreeRepository;

    @Resource
    private ImageService imageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addFolder(FolderModify modify) {
        if(folderRepository.existsAllByAndFolderName(modify.getFolderName())){
            throw new IllegalArgumentException("The folderName already exists the same name!");
        }
        Folder folder = folderRepository.save(new Folder()
                .setFolderName(modify.getFolderName())
                .setParentId(modify.getParentId()));
        FolderTree tree = new FolderTree().setFolderId(folder.getFolderId());
        if(ROOT_ID.equals(modify.getParentId())){
            tree.setPath(Collections.singletonList(folder.getFolderId()));
        }else{
            FolderTree parentTree = folderTreeRepository.findById(modify.getParentId()).orElseThrow( () -> new RuntimeException("The parent of tree doesn't exist!"));
            List<Integer> path = new ArrayList<>(parentTree.getPath().size() + 1);
            path.addAll(parentTree.getPath());
            path.add(folder.getFolderId());
            tree.setPath(path);
        }
        return folderTreeRepository.save(tree).getFolderId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateFolderName(Integer folderId, String name) {
        if(folderRepository.existsAllByFolderIdNotAndFolderName(folderId,name )){
            throw new IllegalArgumentException("The folderName already exists the same name!");
        }
        folderRepository.updateFolderNameByFolderId(name,folderId);
    }

    @Override
    public boolean hasSon(Integer folderId) {
        return folderRepository.existsAllByParentId(folderId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteFolder(Integer folderId)  {
        if(hasSon(folderId)){
            throw new IllegalArgumentException("The folder delete failed. It has son.");
        }
        imageService.deleteAllByFolderId(folderId);
        folderTreeRepository.deleteById(folderId);
        folderRepository.deleteById(folderId);
    }

    @Override
    public List<Folder> findByParentId(Integer parentId) {
        Assert.notNull(parentId, "The parentId cannot be null!");
        return folderRepository.findAllByParentId(parentId);
    }
}
