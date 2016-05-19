class HomeworksController < ApplicationController

  def show
    auth
    @homeworks = Homework.all
  end

  def new
    auth
    @homework = Homework.new
  end

  def create
    auth
    @homework = Homework.new(hw_params)
    if @homework.save
      redirect_to '/homeworks/show'
    else
      redirect_to '/homeworks/new'
    end
  end

  def destroy
    auth
    Homework.find(params[:id]).destroy
    redirect_to '/homeworks/show'
  end

  private
    def hw_params
      params.require(:homework).permit(:date, :homework)
    end

end
