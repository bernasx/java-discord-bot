import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;

public class UserInfo{

    public static void userinfo(MessageReceivedEvent event) {


        String userID = event.getMessage().getAuthor().getId();
        String userAvatar = event.getMessage().getAuthor().getAvatarUrl();
        String userJoinDate = event.getMember().getJoinDate().toString().split("T")[0];
        String userNickname = event.getMember().getEffectiveName();
        event.getChannel().sendMessage(
                new EmbedBuilder().setTitle("User info for " + userNickname).setColor(Color.WHITE)
                        .setThumbnail(userAvatar)
                        .setDescription("\n")
                        .addField("User ID: ", userID, true)
                        .addField("User Join Date: ", userJoinDate, true)
                        .addField("User Roles: ",roleLister(event) , true)
                        .build()).queue();
        System.out.println(roleLister(event));

    }
    public static String roleLister(MessageReceivedEvent event){
        List<Role> roleList = event.getMessage().getMember().getRoles();
        StringBuilder sb = new StringBuilder();
        for(Role x : roleList){
            sb.append(x.getName()).append(", ");
       }
       sb.deleteCharAt(sb.lastIndexOf(","));
    return sb.toString();
  }
}
