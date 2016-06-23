class UsersController < ApplicationController
  def create #this is actually login
    @user = user_params()
    @users = User.all
    login = false

    @users.each do |user|
      if((user["name"] == @user["name"]) && (user["password"]== @user["password"]))
        login = true
        session[:userid] = user.id
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

  def show
    @threads = Array.new()
    @posts = Array.new()
    @user = User.find(session[:userid])

    Post.all.each do |post|
      if post.user == @user
        @posts << post
      end
    end

    ForumThread.all.each do |thread|
      if thread.user == @user
        @threads << thread
      end
    end

  end

  def update
    @user = User.find(session[:userid])
    suc = @user.update(password: user_paramsp()["password"])
    session[:user] = User.find(session[:userid])
    if suc
      session[:updatepsw] = 0
    else
      session[:updatepsw] = 1
    end
    redirect_to '/users/show'
  end


  private
    def user_params()
      params.require(:user).permit(:name, :password)
    end
    def user_paramsp()
      params.require(:user).permit(:password)
    end
end
