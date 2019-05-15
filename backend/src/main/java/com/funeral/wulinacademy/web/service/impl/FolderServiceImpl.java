package com.funeral.wulinacademy.web.service.impl;

import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.entity.FolderTree;
import com.funeral.wulinacademy.web.repository.FolderRepository;
import com.funeral.wulinacademy.web.repository.FolderTreeRepository;
import com.funeral.wulinacademy.web.service.FolderService;
import com.funeral.wulinacademy.web.service.exception.BusinessException;
import com.funeral.wulinacademy.web.service.exception.ValidateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author FuneralObjects
 * @date 2019-05-14 14:45
 */
@Service
public class FolderServiceImpl implements FolderService {

    private static final Integer ROOT_ID = 0;

    @Resource
    private FolderRepository folderRepository;

    @Resource
    private FolderTreeRepository folderTreeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateRootFolder(Folder folder) throws ValidateException {
        Folder saved = folderRepository.save(folder);
        FolderTree tree = hasParent(saved)? createTree(saved,folder.getParentId()): createTree(saved);
        folderTreeRepository.save(tree);
    }

    @Override
    public boolean hasSon(Integer folderId) {
        return folderRepository.existsFolderByParentId(folderId);
    }

    @Override
    public void deleteFolder(Integer folderId) throws ValidateException {
        if(hasSon(folderId)){
            throw new ValidateException("The folder delete failed. It has son.");
        }
    }

    private FolderTree createTree(Folder folder,Integer parentId){
        FolderTree result = this.createTree(folder);
        folderTreeRepository.findById(parentId).ifPresent( folderTree -> result.setPath(lengtheningPath(folder.getFolderId(),folderTree.getPath())));
        return result;
    }

    private FolderTree createTree(Folder folder){
        return new FolderTree().setFolderId(folder.getFolderId()).setPath(String.valueOf(folder.getFolderId()));
    }

    private String lengtheningPath(Integer folderId, String parentPath){
        return parentPath + ":"+ folderId;
    }

    private boolean hasParent(Folder folder) throws ValidateException {
        if(ROOT_ID.equals(folder.getParentId())){
            return false;
        }
        if(folderRepository.findById(folder.getParentId()).orElse(null) == null){
            throw new ValidateException("Invalid parent ["+ folder.getParentId() +"]" );
        }
        return true;
    }
}
