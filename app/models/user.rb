class User < ActiveRecord::Base
  validates :name,  presence: true, length: { maximum: 25, minimum: 3 }
  validates :password,  presence: true, length: { maximum: 25, minimum: 8 }
end
