/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.dto;

import java.util.List;

/**
 *
 * @author 503
 */
public class MessageGroup {
    
    public Integer id;
    public Integer groupId;
    public Integer fromUserId;
    public String content;
    public List<Integer> userIds;
    
}
