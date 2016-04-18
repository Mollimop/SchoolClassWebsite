class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.string :date
      t.string :event

      t.timestamps null: false
    end
  end
end
