class CreateHomeworks < ActiveRecord::Migration
  def change
    create_table :homeworks do |t|
      t.string :date
      t.string :homework

      t.timestamps null: false
    end
  end
end
