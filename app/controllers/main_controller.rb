class MainController < ApplicationController
  def home
    @events = Event.all.reverse
  end
  def login
    @users = User.all
  end

end
