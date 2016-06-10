class EventsController < ApplicationController
  def show
    auth
    @events = Event.all
    @event = Event.new
  end

  def create
    auth
    @event = Event.new(event_params)
    if @event.save
      redirect_to '/events/show'
    else
      redirect_to '/events/show'
    end
  end

  def destroy
    auth
    Event.find(params[:id]).destroy
    redirect_to '/events/show'
  end

  private
    def event_params
      params.require(:event).permit(:date, :event)
    end


end
