class HomeworksController < ApplicationController

  def show
    auth
    @homework = Homework.all
  end

  def new
    auth
    @homework = Homework.new
  end
end
