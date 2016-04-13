class MainController < ApplicationController
  def home
  end

  def events
    @events = Event.all
  end

  def forum
    @threads = Forum_thread.all
  end

  def homework
    @homework = Homework.new
  end

  def newEvent
    @event = Event.new
  end

  def newHomework
    @homework = Homework.new
  end

  def newThread
    @thread = Forum_thread.new
  end

  def newAnswer
    @answer = Post.new
  end

  def login
    @users = User.all
  end
end
