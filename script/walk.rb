require '_script'

on :walk do |c|
  queue = c.player.get_walking_queue
  queue.reset

  c.player.action_queue.clear

  queue.set_running_queue(c.packet.runSteps)
  queue.add_step(c.packet.firstX, c.packet.firstY)
  c.packet.path.each do |step|
    queue.add_step step[0] + c.packet.firstX, step[1] + c.packet.firstY
  end
  queue.finish
end
