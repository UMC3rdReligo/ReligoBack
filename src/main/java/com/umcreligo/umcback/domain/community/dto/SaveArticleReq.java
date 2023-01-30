package com.umcreligo.umcback.domain.community.dto;

import com.umcreligo.umcback.domain.church.domain.Church;
import com.umcreligo.umcback.domain.community.domain.CommunityType;
import com.umcreligo.umcback.domain.user.domain.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class SaveArticleReq {

    private User user;

    private CommunityType type;

    private Church church;

    private String title;

    private String text;
}
