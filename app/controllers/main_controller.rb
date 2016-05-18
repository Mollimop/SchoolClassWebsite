class MainController < ApplicationController
  def home
    auth
    @events = Event.all.reverse
  end
  def login
  end

end
