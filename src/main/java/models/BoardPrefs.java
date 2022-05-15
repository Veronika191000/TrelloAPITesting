package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardPrefs {

    public String permissionLevel;
    public boolean hideVotes;
    public String voting;
    public String comments;
    public String invitations;
    public boolean selfJoin;
    public boolean cardCovers;
    public boolean isTemplate;
    public String cardAging;
    public boolean calendarFeedEnabled;
    public String background;
    public Object backgroundImage;
    public Object backgroundImageScaled;
    public boolean backgroundTile;
    public String backgroundBrightness;
    public String backgroundColor;
    public String backgroundBottomColor;
    public String backgroundTopColor;
    public boolean canBePublic;
    public boolean canBeEnterprise;
    public boolean canBeOrg;
    public boolean canBePrivate;
    public boolean canInvite;
}
