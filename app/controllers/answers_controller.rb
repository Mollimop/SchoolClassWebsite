class AnswersController < ApplicationController

  def new
    @answer = Post.new
  end

  def show
    @answers = Post.new
  end
end
