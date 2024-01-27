package br.com.blog.gateway.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserOptions {

    private String backgroundImg;
    private String profileImg;
    private Color fontColor;

    public UserOptions(){
        this.backgroundImg = "https://cdn.discordapp.com/attachments/832827144216379422/1198365291206426624/licensed-image.jpg?ex=65bea3a5&is=65ac2ea5&hm=9adf419da04e8d765acfe0b17c3a9fc5a3b4f6eff08e9eb6323544dde1e40a16&";
        this.profileImg = "https://cdn.discordapp.com/attachments/832827144216379422/1198365291206426624/licensed-image.jpg?ex=65bea3a5&is=65ac2ea5&hm=9adf419da04e8d765acfe0b17c3a9fc5a3b4f6eff08e9eb6323544dde1e40a16&";
        this.fontColor = Color.BLACK;
    }

    public UserOptions(OptionsDTO dto){
        this.backgroundImg = dto.backgroundImg();
        this.profileImg = dto.profileImg();
        this.fontColor = dto.fontColor();
    }
}
