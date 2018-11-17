import com.google.api.client.json.Json;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

/*
Adds a pre-existing voice role to a user that joins a voice channel in the mentioned category.

Useful to give out for people who want to see a voice text channel to spam links while in voice-chat.

 */


//add voice role
public class VoiceChat extends ListenerAdapter {
    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {

        Role voice = event.getGuild().getRolesByName("voice",true).get(0);
        Member member = event.getMember();

        if (event.getChannelJoined().getParent().getId()
                .equals(JsonIO.JSONReader("voice-category","config.json"))) {
            event.getMember().getGuild().getController().addSingleRoleToMember(member,voice).queue();
        }
    }

    //removes voice role
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {

        Role voice = event.getGuild().getRolesByName("voice",true).get(0);
        Member member = event.getMember();
        GuildController gc = event.getGuild().getController();

        if (event.getChannelLeft().getParent().getId()
                .equals(JsonIO.JSONReader("voice-category","config.json"))) {
            event.getMember().getGuild().getController().removeSingleRoleFromMember(member,voice).queue();
        }//test
    }

}
