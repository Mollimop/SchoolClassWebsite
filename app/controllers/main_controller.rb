class MainController < ApplicationController
  def home
    auth
    @events = Event.all
    @homeworks = Homework.all
  end
  def login
  end

end
