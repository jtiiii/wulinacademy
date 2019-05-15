package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.controller.annotation.AutoValid;
import com.funeral.wulinacademy.web.controller.model.folder.FolderSaveModel;
import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.service.FolderService;
import com.funeral.wulinacademy.web.service.exception.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public void saveFolder(@RequestBody @Valid FolderSaveModel model, BindingResult br) throws BusinessException {
        folderService.saveOrUpdateRootFolder(modelToEntity(model));
    }

    @DeleteMapping("/{id}")
    public void deleteFolder(@PathVariable("id") Integer id){

    }

    private Folder modelToEntity(FolderSaveModel model){
        return new Folder().setFolderName(model.getName())
                .setParentId(model.getParent())
                .setUserId(model.getUser())
                .setStatus(model.getStatus());
    }
}
