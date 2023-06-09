package com.ojeomme.domain.user;

import com.ojeomme.domain.BaseTimeEntity;
import com.ojeomme.domain.user.enums.OauthProvider;
import com.ojeomme.domain.user.enums.converter.OauthProviderConverter;
import com.ojeomme.domain.userowncount.UserOwnCount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Convert(converter = OauthProviderConverter.class)
    @Column(name = "oauth_provider", nullable = false)
    private OauthProvider oauthProvider;

    @Column(name = "nickname", nullable = false, length = 15)
    private String nickname;

    @Column(name = "email", nullable = false, length = 320)
    private String email;

    @Column(name = "profile", nullable = false, length = 2083)
    private String profile;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, optional = false)
    private UserOwnCount userOwnCount;

    @Builder
    public User(Long id, String oauthId, OauthProvider oauthProvider, String nickname, String email, String profile) {
        this.id = id;
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public void modifyProfile(String profile) {
        this.profile = profile;
    }

    public void setDefaultProfile() {
        this.profile = "";
    }
}