import java.awt.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.*;


public class AboutHelpList {
    //info about the bot itself
    public static void about(MessageReceivedEvent event){
        //gets the bot selfuser from getJDA() and its avatar
        String botAvatar = event.getJDA().getSelfUser().getAvatarUrl();

                event.getChannel().sendMessage(
                new EmbedBuilder().setTitle("It's not like I want to tell you about me.")
                        .setDescription(
                        "\n \n B-but if you insist... \n" +
                                "I'm made in Java with the JDA Java Wrapper for Discord. \n \n" +
                        "I can do a lot for you, but only y-you... \n" +
                        "*owo* \n")
                        .setThumbnail(botAvatar)
                        .setFooter("TsundereBot v1.3", null)
                        .setColor(Color.red)
                        .build())
                        .queue();
    }


    //help command with either a generic embed or with a specific command
    public static void help(MessageReceivedEvent event){

        if(event.getMessage().getContentRaw().split(" ").length == 1){
            event.getChannel().sendMessage( new EmbedBuilder().setTitle("I'm not helping you because I want to, baka!!")
                    .setDescription("You can do: ```"+ Commands.prefix +"help command ``` ...to see what a command does! \n \n" +
                            "You can also do: ```"+ Commands.prefix +"list``` ...for a list of all available commands.")
                    .setColor(Color.cyan)
                    .build())
                    .queue();
        }
        else{
            //if someone specifies an help command, this will message users with info about it.
            String helpWith = event.getMessage().getContentRaw().split(" ")[1];

            switch(helpWith){
                case "about": listBuilder(event,"About","This command tells you basic info " +
                        "about the bot.","!about");
                    break;
                case "help": listBuilder(event,"Help","Returns an help screen" +
                        " about the specified command.",""+ Commands.prefix +"help avatar");
                    break;
                case "list": listBuilder(event,"list","Lists all commands available." +
                        "",""+ Commands.prefix +"avatar @testuser#1337");
                    break;
                case "avatar": listBuilder(event,"avatar","This command __**embeds**__ the avatar" +
                        " of the mentioned user and posts it in the channel.",""+ Commands.prefix +"avatar @testuser#1337");
                    break;
                case "rolldice": listBuilder(event,"rolldice","Rolls a 6-sided die."
                        ,""+ Commands.prefix +"rolldice");
                    break;
                case "ping": listBuilder(event,"Ping","Returns a pong! message.",""+ Commands.prefix +"ping");
                    break;
                case "eightball": listBuilder(event,"Eightball","Gives you a random answer to" +
                        " your question.", Commands.prefix +"eightball Are you a tsundere?");
                    break;
                case "**Admin** changeprefix": listBuilder(event,"changeprefix","Changes the prefix of the bot.",
                        Commands.prefix +"changeprefix [New Prefix Here]");
                    break;
                case "say": listBuilder(event,"say","Says whatever you want!",
                        Commands.prefix +"say I'm not saying this for you.");
                    break;
                case "userinfo": listBuilder(event,"userinfo","Gives you info about yourself!",
                        Commands.prefix +"userinfo");
                    break;
                case "decide": listBuilder(event,"decide","Decides for you!",
                        Commands.prefix +"decide movie1 movie2 movie3");
                    break;
                case "joke": listBuilder(event,"joke","Tells you a joke!",
                        Commands.prefix +"joke");
                    break;

                default: event.getChannel().sendMessage("That's not a command!").queue();
                    break;
            }
        }
    }
    //Lists all commands
    public static void list(MessageReceivedEvent event) {

        event.getChannel().sendMessage( new EmbedBuilder().setTitle("Here's a list of all commands, " +
                "I wrote it for myself but you can have it.")
                .setDescription("\n **about** - Info about the bot." +
                        "\n **help** - Help command duh." +
                        "\n **list** - You just used it." +
                        "\n **avatar** - Shows you the avatar of the person mentioned." +
                        "\n **rolldice** - Rolls a dice" +
                        "\n **ping** - Pong!" +
                        "\n **eightball** - Answers all your questions." +
                        "\n **say** - Says whatever you want! "+
                        "\n **userinfo** - Gives you info about yourself."+
                        "\n **decide** - Decides for you!"+
                        "\n **joke** - Tells you a joke!"+
                        "\n \n ----------**ADMIN/MOD ONLY**---------- \n"+
                        "\n **changeprefix** - Changes the prefix of the bot.")
                .setColor(Color.cyan)
                .build())
                .queue();
    }
    //builds the embed for the help command parameters.
    public static void listBuilder(MessageReceivedEvent event, String helpWith, String textDesc, String example) {

        event.getChannel().sendMessage( new EmbedBuilder().setTitle("The " + helpWith + " command.")
                .setDescription("**Description:** " + textDesc + "" +
                        "\n \n **e.g**: " + example)
                .setColor(Color.pink)
                .build())
                .queue();
    }
}

