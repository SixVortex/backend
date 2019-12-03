/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.beans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.entities.Comment;

/**
 *
 * @author Elev
 */
@Stateless
public class CommentBean {
    
    public List<Comment> getComments(int imageID){
       return new ArrayList(); 
    }
    
    public Comment postComment(Comment comment){
        return null;
    }
}
