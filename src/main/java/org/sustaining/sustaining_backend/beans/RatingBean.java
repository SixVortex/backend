/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.beans;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.entities.Rating;

/**
 *
 * @author Elev
 */
@Stateless
public class RatingBean {
    
    public List<Rating> getRatings(int imageID){
        return new ArrayList();
    }
    
    public Rating postRating(Rating rating){
        return null;
    }
}
