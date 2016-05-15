class ThreadsController < ApplicationController

  def show
    @threads = Forum_thread.all
  end

  def new
    @thread = Forum_thread.new
  end
end
