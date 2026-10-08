package com.javarush.quest.controller;

import com.javarush.quest.config.annotation.Servlet;
import com.javarush.quest.config.constant.Schema.Jsp;
import com.javarush.quest.config.constant.Schema.Url;
import com.javarush.quest.controller.base.BaseServlet;

@Servlet(value = Url.USERS, jsp = Jsp.PROFILE)
public class ProfileServlet extends BaseServlet {

}
