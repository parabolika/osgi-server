require '_script'

on :walk do |params|
  queue = params['player'].get_walking_queue
  queue.reset

  params['player'].get_action_queue.clear

  packet = params['packet']
  queue.set_running_queue(packet.runSteps)
  queue.add_step(packet.firstX, packet.firstY)
  packet.path.each do |step|
    queue.add_step step[0] + packet.firstX, step[1] + packet.firstY
  end
  queue.finish
end
