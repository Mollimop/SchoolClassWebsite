class MainController < ApplicationController
  def home
    auth
    @events = Event.all
    @homeworks = Homework.all
    @threads = ForumThread.all
  end
  def login
  end

end
