class AnswersController < ApplicationController

  def new
    auth
    @answer = Post.new
  end

  def show
    auth
    @answers = Post.new
  end
end
