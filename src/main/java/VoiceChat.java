import com.google.api.client.json.Json;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

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
    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {

        Role voice = event.getGuild().getRolesByName("voice",true).get(0);
        Member member = event.getMember();
        GuildController gc = event.getGuild().getController();

        if (event.getChannelLeft().getParent().getId()
                .equals(JsonIO.JSONReader("voice-category","config.json"))) {
            event.getMember().getGuild().getController().removeSingleRoleFromMember(member,voice).queue();
        }
    }

}
