
package com.datengaertnerei.test.pix;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "pageURL",
    "type",
    "tags",
    "previewURL",
    "previewWidth",
    "previewHeight",
    "webformatURL",
    "webformatWidth",
    "webformatHeight",
    "largeImageURL",
    "imageWidth",
    "imageHeight",
    "imageSize",
    "views",
    "downloads",
    "collections",
    "likes",
    "comments",
    "user_id",
    "user",
    "userImageURL"
})
@Generated("jsonschema2pojo")
public class Hit {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("pageURL")
    private String pageURL;
    @JsonProperty("type")
    private String type;
    @JsonProperty("tags")
    private String tags;
    @JsonProperty("previewURL")
    private String previewURL;
    @JsonProperty("previewWidth")
    private Integer previewWidth;
    @JsonProperty("previewHeight")
    private Integer previewHeight;
    @JsonProperty("webformatURL")
    private String webformatURL;
    @JsonProperty("webformatWidth")
    private Integer webformatWidth;
    @JsonProperty("webformatHeight")
    private Integer webformatHeight;
    @JsonProperty("largeImageURL")
    private String largeImageURL;
    @JsonProperty("imageWidth")
    private Integer imageWidth;
    @JsonProperty("imageHeight")
    private Integer imageHeight;
    @JsonProperty("imageSize")
    private Integer imageSize;
    @JsonProperty("views")
    private Integer views;
    @JsonProperty("downloads")
    private Integer downloads;
    @JsonProperty("collections")
    private Integer collections;
    @JsonProperty("likes")
    private Integer likes;
    @JsonProperty("comments")
    private Integer comments;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("user")
    private String user;
    @JsonProperty("userImageURL")
    private String userImageURL;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("pageURL")
    public String getPageURL() {
        return pageURL;
    }

    @JsonProperty("pageURL")
    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("tags")
    public String getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    @JsonProperty("previewURL")
    public String getPreviewURL() {
        return previewURL;
    }

    @JsonProperty("previewURL")
    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    @JsonProperty("previewWidth")
    public Integer getPreviewWidth() {
        return previewWidth;
    }

    @JsonProperty("previewWidth")
    public void setPreviewWidth(Integer previewWidth) {
        this.previewWidth = previewWidth;
    }

    @JsonProperty("previewHeight")
    public Integer getPreviewHeight() {
        return previewHeight;
    }

    @JsonProperty("previewHeight")
    public void setPreviewHeight(Integer previewHeight) {
        this.previewHeight = previewHeight;
    }

    @JsonProperty("webformatURL")
    public String getWebformatURL() {
        return webformatURL;
    }

    @JsonProperty("webformatURL")
    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    @JsonProperty("webformatWidth")
    public Integer getWebformatWidth() {
        return webformatWidth;
    }

    @JsonProperty("webformatWidth")
    public void setWebformatWidth(Integer webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    @JsonProperty("webformatHeight")
    public Integer getWebformatHeight() {
        return webformatHeight;
    }

    @JsonProperty("webformatHeight")
    public void setWebformatHeight(Integer webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    @JsonProperty("largeImageURL")
    public String getLargeImageURL() {
        return largeImageURL;
    }

    @JsonProperty("largeImageURL")
    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    @JsonProperty("imageWidth")
    public Integer getImageWidth() {
        return imageWidth;
    }

    @JsonProperty("imageWidth")
    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    @JsonProperty("imageHeight")
    public Integer getImageHeight() {
        return imageHeight;
    }

    @JsonProperty("imageHeight")
    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    @JsonProperty("imageSize")
    public Integer getImageSize() {
        return imageSize;
    }

    @JsonProperty("imageSize")
    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

    @JsonProperty("views")
    public Integer getViews() {
        return views;
    }

    @JsonProperty("views")
    public void setViews(Integer views) {
        this.views = views;
    }

    @JsonProperty("downloads")
    public Integer getDownloads() {
        return downloads;
    }

    @JsonProperty("downloads")
    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @JsonProperty("collections")
    public Integer getCollections() {
        return collections;
    }

    @JsonProperty("collections")
    public void setCollections(Integer collections) {
        this.collections = collections;
    }

    @JsonProperty("likes")
    public Integer getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @JsonProperty("comments")
    public Integer getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @JsonProperty("user_id")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    @JsonProperty("userImageURL")
    public String getUserImageURL() {
        return userImageURL;
    }

    @JsonProperty("userImageURL")
    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
