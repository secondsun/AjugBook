/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.ajugbook.controller.responses;

import java.util.Set;
import java.util.TreeSet;
import net.saga.ajugbook.vo.Post;
import org.jboss.aerogear.security.model.AeroGearUser;

/**
 *
 * @author Summers
 */
public class IndexResponse {
    
    private Set<PostView> posts = new TreeSet<PostView>();

    public Set<PostView> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostView> posts) {
        
        this.posts = new TreeSet<PostView>(posts);
    }
    
    public static class PostView implements Comparable<PostView>{
        public Post post;
        public AeroGearUser authorUser;

        public Post getPost() {
            return post;
        }

        public void setPost(Post post) {
            this.post = post;
        }

        public AeroGearUser getAuthorUser() {
            return authorUser;
        }

        public void setAuthorUser(AeroGearUser authorUser) {
            this.authorUser = authorUser;
        }

        @Override
        public int compareTo(PostView t) {
            return post.getPosted().compareTo(t.getPost().getPosted());
        }
        
        
        
    }
    
}
