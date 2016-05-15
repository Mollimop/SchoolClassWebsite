class EventsController < ApplicationController
  def show
    @events = Event.all
  end

  def new
    @event = Event.new
  end

  def create
    @event = Event.new(event_params)
    if @event.save
      redirect_to '/events/show'
    else
      redirect_to '/events/new'
    end
  end

  def destroy
    Event.find(params[:id]).destroy
    redirect_to '/events/show'
  end

  private
    def event_params
      params.require(:event).permit(:date, :event)
    end


end
