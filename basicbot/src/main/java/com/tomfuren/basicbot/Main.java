package com.tomfuren.basicbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;

    public static void main(String[] args) {

        //token here(tomoki)
        jdaBuilder = JDABuilder.createDefault("Your tokun");

        //status 宣言 (bot status)
        jdaBuilder.setStatus(OnlineStatus.IDLE);
        jdaBuilder.setActivity(Activity.playing("Hello my friend Im a discord bot made by tomoki"));

        jdaBuilder.addEventListeners(new Main());

        //動きチェック(check build)
        try{
            jdaBuilder.build();
        } catch (LoginException exception){
            exception.printStackTrace();
        }
    }

    //何かmessage を受けた(get some messages)
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if(message.equals("!member")){
            event.getChannel().sendMessage("Thomas Haddon s3902505\nBaran Uysal s3901499\nCollin Ng s3901105\nDomenic Sinni s3900567\nTomoki Sakamoto s3821551").queue();
        }
        String[] arguments = event.getMessage().getContentRaw().split(" ");
        if(arguments[0].equals("!mention")){
            //最初のメンバーを取得する(get first member except offline)
            Member member = event.getMessage().getMentionedMembers().get(0);
            if(member.getUser().isBot()){
                event.getChannel().sendMessage("You've successfully mentioned the use: " + member.getUser().getAsMention()).queue();
            } else {
                event.getChannel().sendMessage("Sorry, but Im not able to mention discord bots!").queue();
            }
        }
    }

    @Override
    //チャンネル名の変更を行った場合に通知を行う（**の使い方が、formatみたいpythonの）
    //when u change the discord channel name, bot will notice it
    public void onGuildUpdateName(@NotNull GuildUpdateNameEvent event) {
        TextChannel textChannel = event.getGuild().getSystemChannel();
        if(textChannel != null){
            String newName = event.getNewName();
            String oldName = event.getOldName();
            textChannel.sendMessage("The discord name was successful changed from **" + oldName + "** to **" + newName + "**!").queue();
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String talking = event.getMessage().getContentRaw();
        if(talking.equals("!sendembed")){
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setTitle("Hey! embed for discord");
            embedBuilder.setColor(0x90c695);
            embedBuilder.setDescription("successfully created your own first message embed");
            embedBuilder.setFooter("simple footer", event.getAuthor().getAvatarUrl());
            embedBuilder.setThumbnail(event.getAuthor().getAvatarUrl());
            embedBuilder.setImage(event.getAuthor().getAvatarUrl());

            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}

