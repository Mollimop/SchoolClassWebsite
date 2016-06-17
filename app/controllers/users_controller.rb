class UsersController < ApplicationController
  def create #this is actually login
    @user = user_params()
    @users = User.all
    login = false

    @users.each do |user|
      if((user["name"] == @user["name"]) && (user["password"]== @user["password"]))
        login = true
        break
      end
    end
    if login
      session[:user] = @user
      redirect_to '/home'
    else
      redirect_to '/login'
    end
  end

  def logout
    session[:user] = User.new(name: "my friend", password: "failed")
    redirect_to '/login'
  end

  private def user_params()
    params.require(:user).permit(:name, :password)
  end
end
