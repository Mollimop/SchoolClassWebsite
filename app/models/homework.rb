class Homework < ActiveRecord::Base
  validates :homework,  presence: true, length: { maximum: 25, minimum: 3 }
  validates :date,  presence: true, length: { maximum: 25, minimum: 5 }
end
