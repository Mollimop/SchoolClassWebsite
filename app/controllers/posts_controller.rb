class PostsController < ApplicationController

  def show
    auth
    @thread = ForumThread.find(params[:id])
    @id = params[:id]
  end

  def new
    auth
    @post = Post.new
  end

  def create
    auth
    @post = Post.new(post_params)
    @post.user = User.find_by(name: "#{User.new(session[:user]).name}")
    @post.forum_thread = ForumThread.find(session[:id])
    if @post.save
      redirect_to "/posts/#{session[:id]}"
    else
      redirect_to "/posts/new"
    end
  end

  def destroy
    auth
    Post.find(params[:id]).destroy
    redirect_to "/posts/#{session[:id]}"
  end

  private
    def post_params
      params.require(:post).permit(:title, :text)
    end

end
