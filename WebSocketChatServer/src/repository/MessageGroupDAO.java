/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;

import models.dto.MessageGroup;

/**
 *
 * @author 503
 */
public interface MessageGroupDAO {
    void insert(MessageGroup messageGroup);
    List<MessageGroup> getMessageGroup(Integer groupId);
}
