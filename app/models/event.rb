class Event < ActiveRecord::Base
  validates :event,  presence: true, length: { maximum: 25, minimum: 3 }
  validates :date,  presence: true, length: { maximum: 25, minimum: 5 }
end
