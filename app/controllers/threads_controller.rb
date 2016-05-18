class ThreadsController < ApplicationController

  def show
    auth
    @threads = Forum_thread.all
  end

  def new
    auth
    @thread = Forum_thread.new
  end
end
