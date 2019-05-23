package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.controller.annotation.AutoValid;
import com.funeral.wulinacademy.web.controller.exception.ForbiddenException;
import com.funeral.wulinacademy.web.controller.model.folder.FolderModel;
import com.funeral.wulinacademy.web.controller.model.folder.FolderSaveModel;
import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.security.SecurityUtils;
import com.funeral.wulinacademy.web.service.FolderService;
import com.funeral.wulinacademy.web.service.exception.BusinessException;
import com.funeral.wulinacademy.web.service.exception.ValidateException;
import com.funeral.wulinacademy.web.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FuneralObjects
 * @date 2019-05-14 15:37
 */
@RestController
@RequestMapping("/folder")
public class FolderController {

    @Resource
    private FolderService folderService;

    @AutoValid
    @PostMapping
    public void saveFolderByCurrentUser(@RequestBody @Valid FolderSaveModel model, BindingResult br) throws BusinessException {
        String user = this.mustLogin();
        if(!FolderService.ROOT_ID.equals(model.getParent())){
            this.validateFolderAuthorityByUser(model.getParent(),user);
        }
        folderService.saveOrUpdateRootFolder(modelToEntity(model).setUserId(user));
    }

    @DeleteMapping("/{id}")
    public void deleteFolder(@PathVariable("id") Integer id) throws ValidateException {
        validateFolderAuthorityByCurrentUser(id);
        folderService.deleteFolder(id);
    }

    @GetMapping("/{id}/son")
    public List<FolderModel> getSons(@PathVariable("id") Integer id) throws ValidateException {
        validateFolderAuthorityByCurrentUser(id);
        return this.entityToModel(folderService.findByParentId(id));
    }

    @GetMapping("/root")
    public List<FolderModel> getUsersRootFolder(){
        String user = mustLogin();
        return this.entityToModel(folderService.findRootFolderByUserId(user));
    }

    private Folder modelToEntity(FolderSaveModel model){
        return new Folder().setFolderName(model.getName())
                .setParentId(model.getParent())
                .setUserId(model.getUser())
                .setStatus(model.getStatus());
    }

    private FolderModel entityToModel(Folder entity){
        FolderModel model = new FolderModel()
                .setCreateTime(entity.getCreateTime())
                .setId(entity.getFolderId())
                .setUpdateTime(entity.getUpdateTime());
        model
                .setName(entity.getFolderName())
                .setParent(entity.getParentId())
                .setStatus(entity.getStatus())
                .setUser(entity.getUserId());
        return model;
    }

    void validateFolderAuthorityByCurrentUser(Integer folderId) throws ForbiddenException {
        String user = mustLogin();
        this.validateFolderAuthorityByUser(folderId,user);
    }
    private void validateFolderAuthorityByUser(Integer folderId, String user){
        if(!folderService.belongUser(folderId,user)){
            throw new ForbiddenException("Permission denied!");
        }
    }

    private String mustLogin(){
        String user = SecurityUtils.getCurrentLoginUsername();
        if(StringUtils.isEmpty(user)){
            throw new ForbiddenException("No login.");
        }
        return user;
    }

    private List<FolderModel> entityToModel(List<Folder> folders){
        return folders.parallelStream().map(this::entityToModel).collect(Collectors.toList());
    }
}
