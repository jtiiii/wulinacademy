package com.funeral.wulinacademy.web.controller;

import com.funeral.wulinacademy.web.controller.model.folder.FolderModifyVo;
import com.funeral.wulinacademy.web.controller.model.folder.FolderVo;
import com.funeral.wulinacademy.web.entity.Folder;
import com.funeral.wulinacademy.web.model.FolderModify;
import com.funeral.wulinacademy.web.service.FolderService;
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

    @PostMapping
    public Integer addFolder(@RequestBody @Valid FolderModifyVo modify) {
        return folderService.addFolder(toModify(modify));
    }

    @DeleteMapping("/{id}")
    public void deleteFolder(@PathVariable("id") Integer id) {
        folderService.deleteFolder(id);
    }

    @GetMapping("/{id}/son")
    public List<FolderVo> getSons(@PathVariable("id") Integer id) {
        return toVo(folderService.findByParentId(id));
    }

    @GetMapping("/root")
    public List<FolderVo> getUsersRootFolder(){
        return toVo(folderService.findByParentId(FolderService.ROOT_ID));
    }

    private FolderModify toModify(FolderModifyVo model){
        return new FolderModify().setParentId(model.getParent()).setFolderName(model.getName());
    }

    private FolderVo toVo(Folder entity){
        return entity == null? null: new FolderVo()
                .setCreateTime(entity.getCreateTime())
                .setId(entity.getFolderId())
                .setName(entity.getFolderName())
                .setParent(entity.getParentId())
                .setUpdateTime(entity.getUpdateTime());
    }

    private List<FolderVo> toVo(List<Folder> folders){
        return folders.parallelStream().map(this::toVo).collect(Collectors.toList());
    }
}
