class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  def auth
    if session[:user]
      @user = session[:user]
    else
      @user = User.new(name: "my friend", password: "failed")
      session[:user] = @user
    end
    @users = User.all
    login = false
    @users.each do |user|
      if((user["name"] == @user["name"]) && (user["password"]== @user["password"]))
        login = true
        break
      end
    end
    redirect_to '/login' unless login
  end

end
