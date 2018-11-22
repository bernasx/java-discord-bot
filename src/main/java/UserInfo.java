import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfo {

    public static void userinfo(MessageReceivedEvent event) {

        String userID = "n";
        String userAvatar = "n";
        String userJoinDate = "n";
        String userNickname = "n";


        //checks if people are pinging others
        if (event.getMessage().getContentRaw().split(" ").length == 1) {
            userID = event.getMessage().getAuthor().getId();
            userAvatar = event.getMessage().getAuthor().getAvatarUrl();
            userJoinDate = event.getMember().getJoinDate().toString().split("T")[0];
            userNickname = event.getMember().getEffectiveName();
        } else if (event.getMessage().getContentRaw().split(" ").length == 2) {

            userID = event.getMessage().getMentionedUsers().stream().limit(1)
                    .map(a -> a.getId()).collect(Collectors.joining());
            userAvatar = event.getMessage().getMentionedUsers().stream().limit(1)
                    .map(u -> u.getAvatarUrl()).collect(Collectors.joining());
            userJoinDate = event.getMessage().getMentionedMembers().stream().limit(1)
                    .map(j -> j.getJoinDate().toString().split("T")[0]).collect(Collectors.joining());
            userNickname = event.getMessage().getMentionedMembers().stream().limit(1)
                    .map(n -> n.getEffectiveName()).collect(Collectors.joining());
        }

        event.getChannel().sendMessage(
                new EmbedBuilder().setTitle("User info for " + userNickname).setColor(Color.WHITE)
                        .setThumbnail(userAvatar)
                        .setDescription("\n")
                        .addField("User ID: ", userID, true)
                        .addField("User Join Date: ", userJoinDate, true)
                        .addField("User Roles: ", roleLister(event), true)
                        .build()).queue();
        System.out.println(roleLister(event));



    }
    public static String roleLister (MessageReceivedEvent event){
        List<Role> roleList = event.getMessage().getMember().getRoles();
        StringBuilder sb = new StringBuilder();
        for (Role x : roleList) {
            sb.append(x.getName()).append(", ");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
}
