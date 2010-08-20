require '_script'

Player = com.parabolika.server.model.Player
class Player
  def action_queue
    (@action_queue ||= [])
  end
end

# TODO: Refactor this.
on :object_action do |c|
  object_loc = Model::Location.create(c.packet.objectX, c.packet.objectY, c.player.get_location.get_z)

  block = lambda do
    if c.player.get_location.is_within_interaction_distance object_loc
      case c.packet.objectId
      when 299
        c.send_message "There's nothing in there!"
      when 823
        c.send_message "You fail to hit the dummy!"
      when 2213
        c.send_message "Cool, have some money."
      end
      true
    else
      false
    end
  end
  c.player.action_queue << block
end
