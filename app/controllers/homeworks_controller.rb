class HomeworksController < ApplicationController

  def show
    @homework = Homework.all
  end

  def new
    @homework = Homework.new
  end
end
