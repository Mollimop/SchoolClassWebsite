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

  def createEvent
    @event = Event.new(event_params)
    if @event.save
      redirect_to events_path
    else
      redirect_to newEvent_path
    end
  end

  # add this: ActiveRecord::Base.connection.execute("DELETE from sqlite_sequence where name = '<table_name>'") tu further destroy actions
  # (after destroying the element) in order to keep the ids low

  private
    def event_params
      params.require(:event).permit(:date, :event)
    end

end
