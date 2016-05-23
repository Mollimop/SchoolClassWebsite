class ForumThreadsController < ApplicationController
  def show
    auth
    @threads = ForumThread.all
  end

  def new
    auth
    @thread = ForumThread.new
  end

  def create
    auth
    @thread = ForumThread.new(thread_params)
    @thread.open = true
    @thread.user = User.find_by(name: "#{User.new(session[:user]).name}")
    if @thread.save
      redirect_to '/forum_threads/show'
    else
      redirect_to '/forum_threads/new'
    end
  end

  def destroy
    auth
    ForumThread.find(params[:id]).destroy
    redirect_to '/forum_threads/show'
  end

  private
    def thread_params
      params.require(:forum_thread).permit(:title, :text, :open, :user)
    end

end
